package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
	"os"
	"time"

	"github.com/labstack/echo"
)

func main() {
	c := http.Client{Timeout: time.Duration(600) * time.Second}
	url := os.Getenv("EXTERNAL_URL")
	if url == "" {
		url = "http://172.17.0.1:9090"
	}

	e := echo.New()
	e.GET("/", func(context echo.Context) error {
		return context.String(http.StatusOK, "Hello, World!")
	})
	e.GET("/external", func(context echo.Context) error {
		resp, err := c.Get(url)
		if err != nil {
			return fmt.Errorf("failed to fetch external URL: %w", err)
		}
		defer resp.Body.Close()
		body, err := ioutil.ReadAll(resp.Body)
		if err != nil {
			return fmt.Errorf("failed to read response body: %w", err)
		}
		return context.Blob(http.StatusOK, "text/html", body)
	})
	e.Logger.Fatal(e.Start(":8080"))
}
