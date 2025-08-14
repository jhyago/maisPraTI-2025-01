import { useParams, Link} from "react-router-dom"

export default function Post() {
    const { id } = useParams()

    return (
        <section>
            <h1>Post #{id}</h1>
            <p>Aqui estariam os dados do post de id #{id}</p>

            <p>
                Ver outro post: <Link to="/posts/10">posts/10</Link>
            </p>
        </section>
    )
}