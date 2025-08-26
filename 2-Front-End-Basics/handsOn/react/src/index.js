import { App } from "./app"

const createRoot = require("react-dom/client")

const container = document.getElementById("root")

const root = createRoot(container)

root.render(
    <App />
)
