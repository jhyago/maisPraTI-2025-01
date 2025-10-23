import { createBrowserRouter, RouterProvider, Navigate } from "react-router-dom"
import { AuthProvider, useAuth } from "../state/auth.jsx"
import LoginPage from "../../pages/login/LoginPage.jsx"
import RegisterPage from "../../pages/register/RegisterPage.jsx"

function PrivateRoute({ children }) {
    const { isAuthenticated } = useAuth()
    if (!isAuthenticated) return <Navigate to="/login" />
    return children
}

export default function AppRouter() {
    
    const router = createBrowserRouter([
        {
            path: '/',
            children: [
                { path: 'login', element: <LoginPage /> },
                { path: 'register', element: <RegisterPage /> }
                // {
                //     path: 'courses',
                //     element: (
                //         <PrivateRoute>
                //             <HomePage />
                //         </PrivateRoute>
                //     )
                // }
            ]
        }
    ])

    return (
        <AuthProvider>
            <RouterProvider router={router} />
        </AuthProvider>
    )
}