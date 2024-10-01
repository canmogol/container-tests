package main

import (
	"fmt"
	"net/http"
    "os"
    "time"
    "io/ioutil"
)

var c http.Client

func hello(w http.ResponseWriter, req *http.Request) {
	fmt.Fprintf(w, "hello world\n")
}

func external(w http.ResponseWriter, req *http.Request) {
    url := os.Getenv("EXTERNAL_URL")
    resp, err := c.Get(url)
    if err != nil {
        fmt.Printf("Error %s", err)
        return
    }
    defer resp.Body.Close()
    body, err := ioutil.ReadAll(resp.Body)
	fmt.Fprintf(w, "%s", body)
}

func main() {
    c = http.Client{Timeout: time.Duration(600) * time.Second}
	http.HandleFunc("/", hello)
	http.HandleFunc("/external", external)
	http.ListenAndServe(":8080", nil)
}
