import { createBrowserRouter, RouterProvider, Navigate } from "react-router-dom"
import { AuthProvider, useAuth } from "../state/auth.jsx"
import LoginPage from "../../pages/login/LoginPage.jsx"
import RegisterPage from "../../pages/register/RegisterPage.jsx"
import Home from "../../pages/home/home.jsx"

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
      { index: true, element: <Navigate to="/login" /> },
      { path: 'login', element: <LoginPage /> },
      { path: 'register', element: <RegisterPage /> },
      { path: '*', element: <h1>404 - Página não encontrada</h1> },
      { path: 'home', element: <Home /> },
      // {
      //   path: 'courses',
      //   element: (
      //     <PrivateRoute>
      //       <HomePage />
      //     </PrivateRoute>
      //   )
      // }
    ],
  },
])


    return (
        <AuthProvider>
            <RouterProvider router={router} />
        </AuthProvider>
    )
}