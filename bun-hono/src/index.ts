import { Context, Hono } from 'hono'
import process from "node:process";

const app = new Hono()
const url = process.env.EXTERNAL_URL || 'http://172.17.0.1:9090';

app.get('/', (c:Context) => {
  return c.text('Hello Hono Bun!')
}).get('/external', async (c:Context) => {
  const resp = await fetch(url);
  const data = await resp.text()
  return c.text(data)
})


export default {
  port: 3000,
  fetch: app.fetch,
}

process.on("SIGINT", () => {
  console.log("Ctrl-C was pressed");
  process.exit();
});
