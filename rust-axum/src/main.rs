use axum::{body::Bytes, response::Html, routing::get, Router};

#[tokio::main]
async fn main() {
    let app = Router::new().route("/external", get(downstream));

    let listener = tokio::net::TcpListener::bind("0.0.0.0:8080").await.unwrap();
    println!("Server is running on http://0.0.0.0:8080");
    axum::serve(listener, app).await.unwrap();
}

async fn downstream() -> Html<String> {
    match send_request().await {
        Ok(body) => Html(String::from_utf8_lossy(&body).into_owned()),
        Err(err) => Html(format!("Error fetching downstream: {}", err)),
    }
}

async fn send_request() -> Result<Bytes, reqwest::Error> {
    let resp = reqwest::get("http://172.17.0.1:9090/")
        .await?
        .bytes()
        .await?;
    Ok(resp)
}
