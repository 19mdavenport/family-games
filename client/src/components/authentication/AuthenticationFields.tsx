
interface Props {
    isBottomField: boolean
    setUsername: (alias: string) => void
    setPassword: (password: string) => void
}

const AuthenticationFields = (props: Props) => {
    return (
        <>
        <div className="form-floating">
          <input
            type="text"
            className="form-control"
            size={50}
            id="usernameInput"
            aria-label={"username"}
            autoComplete="username"
            placeholder="name@example.com"
            onChange={(event) => props.setUsername(event.target.value)}
          />
          <label htmlFor="usernameInput">Username</label>
        </div>
        <div className={`form-floating ${props.isBottomField ? "mb-3" : ""}`}>
          <input
            type="password"
            className={`form-control ${props.isBottomField ? "bottom" : ""}`}
            id="passwordInput"
            aria-label={"password"}
            autoComplete="current-password"
            placeholder="Password"
            onChange={(event) => props.setPassword(event.target.value)}
          />
          <label htmlFor="passwordInput">Password</label>
        </div>
        </>
    );
};
export default AuthenticationFields;