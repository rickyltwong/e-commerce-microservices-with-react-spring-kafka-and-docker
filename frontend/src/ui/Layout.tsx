import React, { ReactNode } from 'react';
import Navbar from '../components/Navbar';

interface LayoutProps {
  children: ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  return <Navbar>{children}</Navbar>;
};

export default Layout;
