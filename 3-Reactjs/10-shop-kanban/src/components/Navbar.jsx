import { NavLink } from "react-router-dom";
import styles from "./Navbar.module.css";

export default function Navbar() {
  return (
    <header className={styles.header}>
      <div className={styles.content}>
        <NavLink to="/" className={styles.logo}>
          <strong>Shopping</strong>
        </NavLink>

        <nav className={styles.actions}>
          <div className={styles.navLinks}>
            <NavLink
              to="/"
              className={({ isActive }) =>
                isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
              }
            >
              Catálogo
            </NavLink>

            <NavLink
              to="/kanban"
              className={({ isActive }) =>
                isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
              }
            >
              Kanban
            </NavLink>
          </div>

          <button type="buton">
            <span className={styles.themeText}></span>
          </button>

          <div className={styles.cart}>
            <span className={styles.cartCount}></span>
          </div>
        </nav>
      </div>
    </header>
  );
}
