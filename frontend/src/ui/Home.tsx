import {
  Container,
  Grid,
  Card,
  Image,
  Text,
  Group,
  Loader,
} from '@mantine/core';
import { notifications } from '@mantine/notifications';
import { QuantitySelector } from '../components';
import { useQuery } from '@tanstack/react-query';

import { Product } from '../types';

import { baseURL } from '../lib/api';

const fetchProducts = async (): Promise<Product[]> => {
  const response = await fetch(`${baseURL}/api/products`);
  if (!response.ok) {
    throw new Error('Network response was not ok');
  }
  return response.json();
};

export default function HomePage() {
  const {
    data: products = [],
    isError,
    isLoading,
  } = useQuery<Product[]>({
    queryKey: ['products'],
    queryFn: fetchProducts,
  });

  const handleAddToCart = (product: Product, quantity: number) => {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    const existingProductIndex = cart.findIndex(
      (item: { productId: string }) => item.productId === product.productId
    );

    if (existingProductIndex !== -1) {
      cart[existingProductIndex].quantity += quantity;
    } else {
      cart.push({ productId: product.productId, quantity });
    }

    localStorage.setItem('cart', JSON.stringify(cart));

    notifications.show({
      title: 'Success',
      message: `${quantity} ${product.name} added to cart`,
      color: 'blue',
    });
    console.log('cart', cart);
  };

  if (isLoading) {
    return (
      <Loader
        size="xl"
        style={{
          width: '100%',
          height: '80vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}
      />
    );
  }

  if (isError) {
    return (
      <Text
        style={{
          width: '100%',
          height: '80vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}>
        Error loading products
      </Text>
    );
  }

  return (
    <Container>
      <Grid>
        {products.map((product) => (
          <Grid.Col key={product.productId} span={4}>
            <Card shadow="lg" p="lg" withBorder>
              <Card.Section>
                <Image
                  src={product.imagePath}
                  height={200}
                  w="auto"
                  alt={product.name}
                  fallbackSrc="https://multimedia.bbycastatic.ca/multimedia/products/500x500/164/16472/16472785.jpg"
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
