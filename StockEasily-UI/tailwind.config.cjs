/* Tailwind cheatsheet: https://tailwindcomponents.com/cheatsheet/ */

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,js,svelte,ts}'],
  theme: {
    extend: {
      height: {
        '10-5': '42px',
      },
      width: {
        '29p': '29%',
        '70p': '70%',
      }
    },
  },
  plugins: [],
}
