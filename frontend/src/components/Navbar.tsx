import React from 'react';
import { Container, Group, Burger, Text, Anchor } from '@mantine/core';
import { useDisclosure } from '@mantine/hooks';
import { useLocation } from 'react-router-dom';
import classes from './Navbar.module.css';
import { IconShoppingCart } from '@tabler/icons-react';

const links = [
  { link: '/', label: 'Products' },
  { link: '/cart', label: <IconShoppingCart /> },
];

interface NavbarProps {
  children: React.ReactNode;
}

const Navbar: React.FC<NavbarProps> = ({ children }) => {
  const [opened, { toggle }] = useDisclosure(false);
  const location = useLocation();

  const items = links.map((link, index) => (
    <Anchor
      key={index}
      href={link.link}
      className={classes.link}
      data-active={location.pathname === link.link ? 'true' : undefined}>
      {link.label}
    </Anchor>
  ));

  return (
    <>
      <header className={classes.header}>
        <Container size="md" className={classes.inner}>
          <Text>DeviceMart</Text>
          <Group gap={5} visibleFrom="xs">
            {items}
          </Group>

          <Burger opened={opened} onClick={toggle} hiddenFrom="xs" size="sm" />
        </Container>
      </header>
      <main>{children}</main>
    </>
  );
};

export default Navbar;
