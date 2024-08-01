import { useEffect, useState } from 'react';
import { Container, Grid, Card, Image, Text, Group } from '@mantine/core';
import { QuantitySelector } from '../components';

import { Product } from '../types';

const sampleProductData = JSON.stringify([
  {
    product_id: '1',
    name: 'iPhone 13',
    description: 'Latest model iPhone with 5G technology',
    price: 999.99,
    category: 'Smartphone',
    image_path: '/app/images/iphone13.jpg',
  },
  {
    product_id: '2',
    name: 'iPhone 12',
    description: 'Previous generation iPhone',
    price: 799.99,
    category: 'Smartphone',
    image_path: '/app/images/iphone12.jpg',
  },
  {
    product_id: '3',
    name: 'iPhone SE',
    description: 'Compact and affordable iPhone',
    price: 499.99,
    category: 'Smartphone',
    image_path: '/app/images/iphonese.jpg',
  },
  {
    product_id: '4',
    name: 'iPad Pro',
    description: 'High performance tablet',
    price: 1099.99,
    category: 'Tablet',
    image_path: '/app/images/ipadpro.jpg',
  },
  {
    product_id: '5',
    name: 'iPad',
    description: 'Lightweight and powerful tablet',
    price: 599.99,
    category: 'Tablet',
    image_path: '/app/images/ipad.jpg',
  },
  {
    product_id: '6',
    name: 'MacBook',
    description: 'Lightweight and powerful laptop',
    price: 999.99,
    category: 'Laptop',
    image_path: '/app/images/macbook.jpg',
  },
]);

export default function HomePage() {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    const parsedData: Product[] = JSON.parse(sampleProductData);
    setProducts(parsedData);
  }, []);

  const handleAddToCart = (product: Product, quantity: number) => {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    const existingProductIndex = cart.findIndex(
      (item: { product_id: string }) => item.product_id === product.product_id
    );

    if (existingProductIndex !== -1) {
      cart[existingProductIndex].quantity += quantity;
    } else {
      cart.push({ product_id: product.product_id, quantity });
    }

    localStorage.setItem('cart', JSON.stringify(cart));
  };

  return (
    <Container>
      <Grid>
        {products.map((product) => (
          <Grid.Col key={product.product_id} span={4}>
            <Card shadow="sm" p="lg">
              <Card.Section>
                <Image
                  src={product.image_path}
                  height={160}
                  alt={product.name}
                />
              </Card.Section>

              <Group style={{ marginBottom: 5, marginTop: 15 }}>
                <Text fw={500}>{product.name}</Text>
                <Text c="blue" fw={700}>
                  ${product.price.toFixed(2)}
                </Text>
              </Group>

              <Text size="sm" c="dimmed">
                {product.description}
              </Text>

              <QuantitySelector
                onAddToCart={(quantity) => handleAddToCart(product, quantity)}
              />
            </Card>
          </Grid.Col>
        ))}
      </Grid>
    </Container>
  );
}
