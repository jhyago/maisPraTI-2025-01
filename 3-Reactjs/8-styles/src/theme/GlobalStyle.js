import { createGlobalStyle } from 'styled-components'

export const GlobalStyle = createGlobalStyle `
    *{ box-sizing: border-box }
    body {
        margin: 0;
        background: ${({theme}) => theme.colors.bg};
        color: ${({theme}) => theme.colors.fg}
    }
`