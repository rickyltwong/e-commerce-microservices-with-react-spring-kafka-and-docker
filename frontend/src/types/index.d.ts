export interface Product {
  productId: string;
  name: string;
  description: string;
  price: number;
  category: string;
  imagePath: string;
  quantity: number;
}

export interface Transaction {
  orderId: string;
  orderDate: string;
  totalAmount: number;
  orderItems: OrderItem[];
}

export interface OrderItem {
  orderItemId: string;
  productId: string;
  quantity: number;
}
