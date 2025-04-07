import React from 'react';
import { render } from '@testing-library/react';
import LoadingSpinner from '../LoadingSpinner';

describe('LoadingSpinner', () => {
    it('should render with default props', () => {
        const { container } = render(<LoadingSpinner />);
        expect(container.querySelector('.w-8.h-8')).toBeInTheDocument();
    });

    it('should render with small size', () => {
        const { container } = render(<LoadingSpinner size="small" />);
        expect(container.querySelector('.w-4.h-4')).toBeInTheDocument();
    });

    it('should render with medium size', () => {
        const { container } = render(<LoadingSpinner size="medium" />);
        expect(container.querySelector('.w-8.h-8')).toBeInTheDocument();
    });

    it('should render with large size', () => {
        const { container } = render(<LoadingSpinner size="large" />);
        expect(container.querySelector('.w-12.h-12')).toBeInTheDocument();
    });

    it('should render with fullScreen prop', () => {
        const { container } = render(<LoadingSpinner fullScreen />);
        expect(container.querySelector('.fixed.inset-0')).toBeInTheDocument();
    });

    it('should render without fullScreen prop', () => {
        const { container } = render(<LoadingSpinner />);
        expect(container.querySelector('.fixed.inset-0')).not.toBeInTheDocument();
    });
}); 