import { defineConfig } from 'vite';

export default defineConfig({
  root: 'web-client',
  base: '/CSCE548Projects/',

  build: {
    outDir: '../dist',
    emptyOutDir: true
  }
});
