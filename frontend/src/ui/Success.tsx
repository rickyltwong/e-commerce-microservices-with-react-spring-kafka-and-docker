import { useEffect, useState } from 'react';
import { Container, Text, Button, Group, Box, Paper } from '@mantine/core';
import { IconCheck } from '@tabler/icons-react';
import { useQuery } from '@tanstack/react-query';
import { OrderItem, Transaction, Product } from '../types';

import { baseURL } from '../lib/api';

export default function Success(): React.JSX.Element {
  const [transaction, setTransaction] = useState<Transaction | null>(null);

  const {
    data: products = [],
    isLoading,
    isError,
  } = useQuery<Product[]>({
    queryKey: ['products'],
    queryFn: async () => {
      const response = await fetch(`${baseURL}/api/products`);
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    },
  });

  useEffect(() => {
    const savedTransaction = localStorage.getItem('latestTransaction');
    if (savedTransaction) {
      setTransaction(JSON.parse(savedTransaction));
    }
  }, []);

  function handleContinueShopping() {
    window.location.href = '/';
  }

  if (isLoading) return <Text>Loading products...</Text>;
  if (isError) return <Text>Error fetching products.</Text>;

  const getProduct = (productId: string) => {
    return products.find((p) => p.productId === productId);
  };

  return (
    <Container size="sm" style={{ textAlign: 'left', marginTop: '5rem' }}>
      <Box mb={20} style={{ textAlign: 'center' }}>
        <IconCheck size={60} color="green" />
      </Box>
      <Text size="xl" fw={700} mb={10} style={{ textAlign: 'left' }}>
        Purchase Successful!
      </Text>
      <Text size="md" c="dimmed" mb={30} style={{ textAlign: 'left' }}>
        Thank you for your purchase. Your order has been placed successfully.
      </Text>

      {transaction && (
        <Paper shadow="xs" withBorder p="xl" style={{ textAlign: 'left' }}>
          <Text size="md" fw={500} mb={10}>
            Order ID: {transaction.orderId}
          </Text>
          <Text size="md" fw={500} mb={10}>
            Order Date: {new Date(transaction.orderDate).toLocaleString()}
          </Text>
          <Text size="md" fw={500} mb={10}>
            Total Amount: ${transaction.totalAmount.toFixed(2)}
          </Text>
          <Text size="md" fw={500} mb={10}>
            Items:
          </Text>
          <ul>
            {transaction.orderItems.map((item: OrderItem) => (
              <li key={item.orderItemId}>
                {item.quantity} x {getProduct(item.productId)?.name}
              </li>
            ))}
          </ul>
        </Paper>
      )}

      <Group justify="center" mt={30}>
        <Button variant="light" color="blue" onClick={handleContinueShopping}>
          Continue Shopping
        </Button>
      </Group>
    </Container>
  );
}
