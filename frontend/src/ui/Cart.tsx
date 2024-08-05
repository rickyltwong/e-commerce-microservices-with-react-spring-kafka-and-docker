import { useEffect, useState } from 'react';
import {
  Container,
  Grid,
  Image,
  Text,
  Group,
  Button,
  Box,
  UnstyledButton,
} from '@mantine/core';
import { IconPlus, IconMinus, IconTrash } from '@tabler/icons-react';

interface Product {
  product_id: string;
  name: string;
  description: string;
  price: number;
  category: string;
  image_path: string;
}

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

export default function Cart(): React.JSX.Element {
  const [cartItems, setCartItems] = useState<
    { product: Product; quantity: number }[]
  >([]);
  const [totalPrice, setTotalPrice] = useState(0);

  useEffect(() => {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    const parsedProductData: Product[] = JSON.parse(sampleProductData);
    const items = cart.map((item: { product_id: string; quantity: number }) => {
      const product = parsedProductData.find(
        (p) => p.product_id === item.product_id
      );
      return { product: product as Product, quantity: item.quantity };
    });
    setCartItems(items);
  }, []);

  useEffect(() => {
    const total = cartItems.reduce(
      (sum, item) => sum + item.product.price * item.quantity,
      0
    );
    setTotalPrice(total);
  }, [cartItems]);

  const handleQuantityChange = (productId: string, delta: number) => {
    setCartItems((currentItems) =>
      currentItems.map((item) =>
        item.product.product_id === productId
          ? { ...item, quantity: Math.max(1, item.quantity + delta) }
          : item
      )
    );
  };

  const handleDelete = (productId: string) => {
    const updatedCartItems = cartItems.filter(
      (item) => item.product.product_id !== productId
    );
    setCartItems(updatedCartItems);
    localStorage.setItem(
      'cart',
      JSON.stringify(
        updatedCartItems.map((item) => ({
          product_id: item.product.product_id,
          quantity: item.quantity,
        }))
      )
    );
  };

  const handleCheckout = () => {
    // Implement checkout functionality
    console.log('Checkout clicked');
  };

  return (
    <Container>
      <Text size="xl" fw={700} mb={20}>
        Shopping Cart
      </Text>
      {cartItems.map(({ product, quantity }) => (
        <Box
          key={product.product_id}
          mb={20}
          p={10}
          style={{ border: '1px solid #e0e0e0', borderRadius: 8 }}>
          <Grid align="center">
            <Grid.Col span={2}>
              <Image
                src={product.image_path}
                height={80}
                width={80}
                fit="cover"
                alt={product.name}
              />
            </Grid.Col>
            <Grid.Col span={3}>
              <Text fw={500}>{product.name}</Text>
              <Text size="sm" c="dimmed">
                {product.description}
              </Text>
            </Grid.Col>
            <Grid.Col span={2}>
              <Text c="blue" fw={700}>
                ${product.price.toFixed(2)}
              </Text>
            </Grid.Col>
            <Grid.Col span={3}>
              <Group>
                <UnstyledButton
                  onClick={() => handleQuantityChange(product.product_id, -1)}>
                  <IconMinus size={16} />
                </UnstyledButton>
                <Text>{quantity}</Text>
                <UnstyledButton
                  onClick={() => handleQuantityChange(product.product_id, 1)}>
                  <IconPlus size={16} />
                </UnstyledButton>
              </Group>
            </Grid.Col>
            <Grid.Col span={2}>
              <UnstyledButton onClick={() => handleDelete(product.product_id)}>
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
