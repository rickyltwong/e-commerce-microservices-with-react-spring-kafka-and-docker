import { Container, Text, Button, Group, Box } from '@mantine/core';
import { IconCheck } from '@tabler/icons-react';

export default function Success(): React.JSX.Element {
  function handleContinueShopping() {
    window.location.href = '/';
  }

  return (
    <Container size="sm" style={{ textAlign: 'center', marginTop: '5rem' }}>
      <Box mb={20}>
        <IconCheck size={60} color="green" />
      </Box>
      <Text size="xl" fw={700} mb={10}>
        Purchase Successful!
      </Text>
      <Text size="md" c="dimmed" mb={30}>
        Thank you for your purchase. Your order has been placed successfully.
      </Text>
      <Group justify="center">
        <Button variant="light" color="blue" onClick={handleContinueShopping}>
          Continue Shopping
        </Button>
      </Group>
    </Container>
  );
}
