package main

import (
    "fmt"
    "os"
    "time"

    "github.com/gofiber/fiber/v2"
    "github.com/gofiber/fiber/v3/client"
)

func main() {
    app := fiber.New()
    url := os.Getenv("EXTERNAL_URL")
    if url == "" {
        url = "http://172.17.0.1:9090"
    }
    cc := client.New()
    cc.SetTimeout(3 * time.Second)

    app.Get("/", func(c *fiber.Ctx) error {
        return c.SendString("hello fiber")
    })

    app.Get("/external", func(c *fiber.Ctx) error {
        resp, err := cc.Get(url)
        if err != nil {
            return fiber.NewError(fiber.StatusInternalServerError, "GET Error!")
        }

        return c.SendString(string(resp.Body()))
    })

    fmt.Println("Server started at 8080")
    app.Listen(":8080")
}
