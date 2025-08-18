import styled, { ThemeProvider } from 'styled-components'
import { theme } from '../../theme/theme'

const Btn = styled.button `
    padding: 12px 16px;
    background: ${({$variant, theme}) => 
        $variant === 'ghost' ? 'transparent' : theme.colors.fg};
    cursor: pointer;
`

export function Themed({children}) {
    return <ThemeProvider theme={theme}>{children}</ThemeProvider>
}

export function ButtonStyled({variant='solid', children}) {
    return <Btn $variant={variant}>{children}</Btn>
}