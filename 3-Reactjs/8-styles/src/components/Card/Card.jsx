export function Card({title, children}) {
    return (
        <div className="card">
            <h2 className="card__title">{title}</h2>
            <div>{children}</div>
        </div>
    )
}