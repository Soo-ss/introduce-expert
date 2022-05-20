/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
};

module.exports = {
  nextConfig,
  // getServerSideProps는 상관없지만 프론트에서 쏘는건 가려야되므로 설정해준다.
  async rewrites() {
    if (process.env.NODE_ENV !== "production") {
      return [
        {
          source: process.env.SOURCE_PATH,
          destination: process.env.DESTINATION_URL,
        },
      ];
    }
  },
};
