use axum::{
    extract::State,
    response::Html,
    routing::get,
    Router,
};
use reqwest::Client;

#[tokio::main(flavor = "multi_thread", worker_threads = 8)]
async fn main() {
    // Create one shared reqwest client (reused across requests)
    let client = Client::builder()
        // raise pool size so we can keep more idle connections per-host
        .pool_max_idle_per_host(100)
        .build()
        .expect("failed to build reqwest client");

    // Store client in router state so handlers can reuse it cheaply
    let app = Router::new()
        .route("/external", get(downstream))
        .with_state(client);

    let listener = tokio::net::TcpListener::bind("0.0.0.0:8080").await.unwrap();
    println!("Server is running on http://0.0.0.0:8080");
    axum::serve(listener, app).await.unwrap();

}

async fn downstream(State(client): State<Client>) -> Html<String> {
    match client.get("http://172.17.0.1:9090/").send().await {
        Ok(resp) => match resp.bytes().await {
            Ok(body) => Html(String::from_utf8_lossy(&body).into_owned()),
            Err(e) => Html(format!("Error reading body: {}", e)),
        },
        Err(e) => Html(format!("Error fetching downstream: {}", e)),
    }
}
