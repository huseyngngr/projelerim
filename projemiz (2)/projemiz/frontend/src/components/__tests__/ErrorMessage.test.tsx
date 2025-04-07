import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import ErrorMessage from '../ErrorMessage';

describe('ErrorMessage', () => {
    it('should render error message', () => {
        render(<ErrorMessage message="Test error message" />);
        expect(screen.getByText('Test error message')).toBeInTheDocument();
    });

    it('should render close button when onClose prop is provided', () => {
        render(<ErrorMessage message="Test error message" onClose={() => {}} />);
        expect(screen.getByText('Kapat')).toBeInTheDocument();
    });

    it('should not render close button when onClose prop is not provided', () => {
        render(<ErrorMessage message="Test error message" />);
        expect(screen.queryByText('Kapat')).not.toBeInTheDocument();
    });

    it('should call onClose when close button is clicked', () => {
        const onCloseMock = jest.fn();
        render(<ErrorMessage message="Test error message" onClose={onCloseMock} />);
        
        fireEvent.click(screen.getByText('Kapat'));
        expect(onCloseMock).toHaveBeenCalledTimes(1);
    });
}); 