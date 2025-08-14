import { Outlet } from "react-router"
import NavBar from "../components/Navbar"

export default function RootLayout() {
    return(
        <div style={{ fontFamily: "system-ui, Arial", lineHeight: 1.5}}>
            <NavBar />

            <main style={{ padding: 16 }}>
                <Outlet />
            </main>
        </div>
    )
}