import { Context, Hono } from 'hono'
import process from "node:process";

const app = new Hono()

app.get('/', (c:Context) => {
  return c.text('Hello Hono Bun!')
})

export default app

process.on("SIGINT", () => {
  console.log("Ctrl-C was pressed");
  process.exit();
});