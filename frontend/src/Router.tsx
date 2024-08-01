import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './ui/Home';
import { Cart, Success, Layout } from './ui';

const router = createBrowserRouter([
  {
    path: '/',
    element: (
      <Layout>
        <Home />
      </Layout>
    ),
  },
  {
    path: '/cart',
    element: (
      <Layout>
        <Cart />
      </Layout>
    ),
  },
  {
    path: '/success',
    element: (
      <Layout>
        <Success />
      </Layout>
    ),
  },
]);

export function Router() {
  return <RouterProvider router={router} />;
}
