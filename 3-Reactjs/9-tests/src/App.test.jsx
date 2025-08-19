import { render, screen, fireEvent } from '@testing-library/react'
import { describe, it, expect } from 'vitest'
import App from './App'

describe('App', () => {
    it('Deve renderizar um componente de contador e interagir com ele', () => {
        render(<App />)

        const countElement = screen.getByTestId('count')
        const buttonElement = screen.getByText('Incrementar')

        expect(countElement).toHaveTextContent('0')

        fireEvent.click(buttonElement)
        expect(countElement).toHaveTextContent('1')
        fireEvent.click(buttonElement)
        expect(countElement).toHaveTextContent('2')
    })
})