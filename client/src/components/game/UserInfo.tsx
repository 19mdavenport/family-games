import "./UserInfo.css"
import React, {useState} from "react";

interface Props {
  style: React.CSSProperties;
}

const UserInfo = (props: Props) => {
  const [imageUrl, setImageUrl] = useState<string>("no-profile-picture.svg");
  const [displayName, setDisplayName] = useState<string>("steve");
  return (
    <a href={`./users/${displayName}`} target="_blank" style={props.style}>
      <img src={imageUrl}  alt="" className="userInfoImage"/>
      <span className="userInfoDisplayName">{displayName}</span>
    </a>
  )
}
export default UserInfo