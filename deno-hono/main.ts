import {Context, Hono} from 'hono'

const app = new Hono()

app.get('/', (c: Context) => {
    return c.text('Hello World')
}).get('/external', async (c: Context) => {
    const externalUrl = Deno.env.get("EXTERNAL_URL")
    const resp = await fetch(externalUrl);
    const data =  await resp.text()
    return c.text(await data)
})

Deno.serve({port: 8080}, app.fetch)
