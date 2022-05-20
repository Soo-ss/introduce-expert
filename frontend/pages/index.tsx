import axios from "axios";
import Seo from "../components/Seo";

export default function Home({ user }: any) {
  console.log(user.bigData.data2);
  return (
    <div>
      <Seo title="Home page" />
      <h1>Home page</h1>
    </div>
  );
}

export async function getServerSideProps() {
  try {
    // client에서 요청 url이 안보인다. 여기서 다 처리하고 넘기기때문에 그런듯.
    const res = await axios.get("http://localhost:8080/api/test");
    if (res.status === 200) {
      const user = res.data;
      return {
        props: { user },
      };
    }
  } catch (error) {
    console.log(error);
    return {
      props: {},
    };
  }
}
