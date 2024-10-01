import { Context, Hono } from 'hono'
import process from "node:process";

const app = new Hono()

app.get('/', (c:Context) => {
  return c.text('Hello Hono Bun!')
}).get('/external', async (c:Context) => {
  const resp = await fetch(process.env.EXTERNAL_URL);
  const data =  await resp.text()
  return c.text(await data)
})

export default app

process.on("SIGINT", () => {
  console.log("Ctrl-C was pressed");
  process.exit();
});
