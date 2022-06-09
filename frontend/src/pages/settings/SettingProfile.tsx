import React, { useRef, useState } from "react";
import SettingsBar from "../../partials/SettingsBar";
import { Cropper } from "react-cropper";
import "cropperjs/dist/cropper.css";
import axios from "axios";
import useReduxAuth from "../../redux/hooks/useReduxAuth";

function SettingProfile() {
  const defaultSrc =
    "https://raw.githubusercontent.com/roadmanfong/react-cropper/master/example/img/child.jpg";
  const [image, setImage] = useState(defaultSrc);
  const [cropData, setCropData] = useState("#");
  const [cropper, setCropper] = useState<any>();
  const [textData, setTextData] = useState({
    bio: "",
    url: "",
    occupation: "",
    location: "",
  });
  const { authInfo } = useReduxAuth();

  function onChange(e: any) {
    e.preventDefault();
    let files;
    if (e.dataTransfer) {
      files = e.dataTransfer.files;
    } else if (e.target) {
      files = e.target.files;
    }
    const reader = new FileReader();
    reader.onload = () => {
      setImage(reader.result as any);
    };
    reader.readAsDataURL(files[0]);
  }

  function getCropData() {
    if (typeof cropper !== "undefined") {
      setCropData(cropper.getCroppedCanvas().toDataURL());
    }
  }

  function handleChange(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    let formData = new FormData(event.currentTarget);
    let bio = formData.get("bio") as string;
    let url = formData.get("url") as string;
    let occupation = formData.get("occupation") as string;
    let location = formData.get("location") as string;

    setTextData({ bio, url, occupation, location });
  }

  function onSubmit() {
    console.log(textData);
    console.log(cropData);

    const body = {
      email: authInfo.email,
      bio: textData.bio,
      url: textData.url,
      occupation: textData.occupation,
      location: textData.location,
      profileImage: cropData,
    };

    axios
      .post("/api/settings/profile", body, {
        headers: { "content-type": "application/json" },
      })
      .then((res) => {
        console.log(res.data);
      });
  }

  return (
    <div>
      <h1>프로필 수정 폼</h1>
      <SettingsBar />
      <form onChange={handleChange}>
        <p>한 줄 소개</p>
        <label>
          <input name="bio" type="text" />
        </label>
        <p>링크 (URL)</p>
        <label>
          <input name="url" type="text" />
        </label>
        <p>직업</p>
        <label>
          <input name="occupation" type="text" />
        </label>
        <p>활동 지역</p>
        <label>
          <input name="location" type="text" />
        </label>
      </form>
      <p>======================================================</p>
      <div>
        <div style={{ width: "100%" }}>
          <input type="file" onChange={onChange} />
          <button>Use default img</button>
          <br />
          <br />
          <Cropper
            style={{ height: 400, width: "100%" }}
            zoomTo={0.5}
            initialAspectRatio={1}
            preview=".img-preview"
            src={image}
            viewMode={1}
            minCropBoxHeight={10}
            minCropBoxWidth={10}
            background={false}
            responsive={true}
            autoCropArea={1}
            checkOrientation={false} // https://github.com/fengyuanchen/cropperjs/issues/671
            onInitialized={(instance) => {
              setCropper(instance);
            }}
            guides={true}
          />
        </div>
        <div>
          <div
            style={{
              width: "50%",
              float: "left",
              height: "300px",
              display: "inline-block",
              padding: "10px",
              boxSizing: "border-box",
            }}>
            <h1>
              <span>Crop</span>
              <button style={{ float: "right" }} onClick={getCropData}>
                Crop Image
              </button>
            </h1>
            <img style={{ width: "50%" }} src={cropData} alt="cropped" />
          </div>
        </div>
        {/* <br style={{ clear: "both" }} /> */}
      </div>
      <br />
      <button style={{ padding: "20px" }} onClick={onSubmit}>
        수정하기
      </button>
    </div>
  );
}

export default SettingProfile;
