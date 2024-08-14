import { useEffect, useState } from 'react';
import {
  Container,
  Grid,
  Loader,
  Image,
  Text,
  Group,
  Button,
  Box,
  UnstyledButton,
} from '@mantine/core';
import { IconPlus, IconMinus, IconTrash } from '@tabler/icons-react';
import { useQuery } from '@tanstack/react-query';
import { baseURL } from '../lib/api';
import { Product } from '../types';
import { notifications } from '@mantine/notifications';

export default function Cart(): React.JSX.Element {
  const [cartItems, setCartItems] = useState<
    { product: Product; quantity: number }[]
  >([]);
  const [totalPrice, setTotalPrice] = useState(0);

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
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    const items = cart.map((item: { productId: string; quantity: number }) => {
      const product = products.find((p) => p.productId === item.productId);
      return { product, quantity: item.quantity };
    });
    setCartItems(items);
  }, [products]);

  useEffect(() => {
    const total = cartItems.reduce((sum, item) => {
      if (item.product) {
        return sum + item.product.price * item.quantity;
      }
      return sum;
    }, 0);
    setTotalPrice(total);
  }, [cartItems]);

  const handleQuantityChange = (productId: string, delta: number) => {
    setCartItems((currentItems) =>
      currentItems.map((item) =>
        item.product?.productId === productId
          ? { ...item, quantity: Math.max(1, item.quantity + delta) }
          : item
      )
    );
  };

  const handleDelete = (productId: string) => {
    const updatedCartItems = cartItems.filter(
      (item) => item.product?.productId !== productId
    );
    setCartItems(updatedCartItems);
    localStorage.setItem(
      'cart',
      JSON.stringify(
        updatedCartItems.map((item) => ({
          productId: item.product?.productId,
          quantity: item.quantity,
        }))
      )
    );
  };

  const handleCheckout = async () => {
    const orderItems = cartItems.map((item) => ({
      productId: item.product?.productId,
      quantity: item.quantity,
    }));

    const orderRequest = {
      orderItems,
    };

    try {
      const response = await fetch(`${baseURL}/api/orders`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(orderRequest),
      });

      if (!response.ok) {
        throw new Error('Failed to submit order');
      }

      const orderResponse = await response.json();

      localStorage.setItem('latestTransaction', JSON.stringify(orderResponse));

      setCartItems([]);
      setTotalPrice(0);
      localStorage.removeItem('cart');

      window.location.href = '/success';
    } catch (error) {
      notifications.show({
        title: 'Error',
        message: 'Failed to submit order',
        color: 'red',
      });
      console.error('Error during checkout:', error);
    }
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

  if (cartItems.length === 0) {
    return (
      <Container>
        <Text size="xl" fw={700} mb={20}>
          Shopping Cart
        </Text>
        <Text>Your cart is currently empty.</Text>
      </Container>
    );
  }

  return (
    <Container>
      <Text size="xl" fw={700} mb={20}>
        Shopping Cart
      </Text>
      {cartItems.map(({ product, quantity }) => (
        <Box
          key={product?.productId}
          mb={20}
          p={10}
          style={{ border: '1px solid #e0e0e0', borderRadius: 8 }}>
          <Grid align="center">
            <Grid.Col span={2}>
              <Image
                src={product?.imagePath}
                height={80}
                width={80}
                fit="cover"
                alt={product?.name}
              />
            </Grid.Col>
            <Grid.Col span={3}>
              <Text fw={500}>{product?.name}</Text>
              <Text size="sm" c="dimmed">
                {product?.description}
              </Text>
            </Grid.Col>
            <Grid.Col span={2}>
              <Text c="blue" fw={700}>
                ${product?.price.toFixed(2)}
              </Text>
            </Grid.Col>
            <Grid.Col span={3}>
              <Group>
                <UnstyledButton
                  onClick={() =>
                    handleQuantityChange(product?.productId ?? '', -1)
                  }>
                  <IconMinus size={16} />
                </UnstyledButton>
                <Text>{quantity}</Text>
                <UnstyledButton
                  onClick={() =>
                    handleQuantityChange(product?.productId ?? '', 1)
                  }>
                  <IconPlus size={16} />
                </UnstyledButton>
              </Group>
            </Grid.Col>
            <Grid.Col span={2}>
              <UnstyledButton
                onClick={() => handleDelete(product?.productId ?? '')}>
                <IconTrash size={20} color="red" />
              </UnstyledButton>
            </Grid.Col>
          </Grid>
        </Box>
      ))}
      <Box mt={20}>
        <Text size="lg" fw={500}>
          Total Price: ${totalPrice.toFixed(2)}
        </Text>
      </Box>
      <Group justify="right" mt={20}>
        <Button variant="light" color="blue" onClick={handleCheckout}>
          Checkout
        </Button>
      </Group>
    </Container>
  );
}
