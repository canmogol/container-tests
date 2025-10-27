use actix_web::{App, HttpResponse, HttpServer, Responder, web};
use awc::Client;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    println!("Server running on http://localhost:8080");
    
    HttpServer::new(move || {
        App::new()
            .app_data(web::Data::new(Client::default()))
            .route("/external", web::get().to(external))
    })
    .bind(("0.0.0.0", 8080))?
    .run()
    .await
}

async fn external(client: web::Data<Client>) -> impl Responder {
    let builder = client.get("http://172.17.0.1:9090/");
    let res = builder.send().await;
    match res {
        Ok(mut response) => {
            let body = response.body().await;
            match body {
                Ok(bytes) => HttpResponse::Ok().body(bytes),
                Err(_) => HttpResponse::InternalServerError().body("Failed to read response body"),
            }
        }
        Err(_) => HttpResponse::InternalServerError().body("Failed to make request"),
    }
}
