import ButtonStyle from './ButtonModuleExample.module.css'

export function ButtonModuleExample({children}) {
    return <button className={ButtonStyle.btn}>{children}</button>
}