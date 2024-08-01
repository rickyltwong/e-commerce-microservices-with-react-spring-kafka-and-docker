import { useState } from 'react';
import { UnstyledButton, Button, Group, Text, Box } from '@mantine/core';
import { IconPlus, IconMinus } from '@tabler/icons-react';

interface QuantitySelectorProps {
  onAddToCart: (quantity: number) => void;
}

const QuantitySelector = ({ onAddToCart }: QuantitySelectorProps) => {
  const [quantity, setQuantity] = useState(1);

  const handleIncrease = () => setQuantity((prev) => Math.min(prev + 1, 99));
  const handleDecrease = () => setQuantity((prev) => Math.max(prev - 1, 1));

  return (
    <Group grow style={{ marginTop: 14 }}>
      <Box>
        <UnstyledButton onClick={handleDecrease}>
          <IconMinus size={18} />
        </UnstyledButton>
        <Text style={{ display: 'inline', margin: '0 10px' }} size="lg">
          {quantity}
        </Text>
        <UnstyledButton onClick={handleIncrease}>
          <IconPlus size={18} />
        </UnstyledButton>
      </Box>
      <Button onClick={() => onAddToCart(quantity)}>Add to cart</Button>
    </Group>
  );
};

export default QuantitySelector;
