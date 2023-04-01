import { useState } from "react";
import { Alert, Button, Form, Modal } from "react-bootstrap";
import { mutate } from "swr";

export default function LoginPopup({ show, onHide, link }) {
  const [error, setError] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();

    const form = e.target;
    const formData = new FormData(form);
    const response = await fetch(link.href, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: formData.get("username"),
        password: formData.get("password"),
      }),
    });

    if (response.ok) {
      mutate("/api/users/current");
      setError(false);
      onHide();
    } else {
      setError(true);
    }
  }

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>Sign In</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {error && <Alert variant="danger">Wrong credentials!</Alert>}
        <Form className="d-grid gap-2" onSubmit={handleSubmit}>
          <Form.Group controlId="formGroupEmail">
            <Form.Label>Email address</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter email"
              name="username"
            />
          </Form.Group>
          <Form.Group controlId="formGroupPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              placeholder="Password"
              name="password"
            />
          </Form.Group>

          <div className="row mb-3">
            <div className="col d-flex justify-content-center">
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  value=""
                  id="form2Example31"
                  checked=""
                />
                <label className="form-check-label" for="form2Example31">
                  Remember me
                </label>
              </div>
            </div>

            <div className="col">
              <a href="#!">Forgot password?</a>
            </div>
          </div>

          <Button type="submit" className="btn-block mb-3">
            Sign in
          </Button>

          <div className="text-center">
            <p>
              Not a member? <a href="#!">Register</a>
            </p>
          </div>
        </Form>
      </Modal.Body>
    </Modal>
  );
}
