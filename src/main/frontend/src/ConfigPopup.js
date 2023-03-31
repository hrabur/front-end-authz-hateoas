import { Button, Form, FormControl, Modal } from "react-bootstrap";
import { useForm } from "react-hook-form";

export default function ConfigPopup({ show, onHide }) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    defaultValues: { greeting: "Welcome", maxGuesses: 9 },
  });
  const onSubmit = (data) => console.log(data);
  console.log(errors);

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>Configuration</Modal.Title>
      </Modal.Header>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Modal.Body>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Greeting</Form.Label>
            <FormControl
              placeholder="Greeting"
              {...register("greeting", { required: true, maxLength: 19 })}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Max guesses</Form.Label>
            <FormControl
              placeholder="Max guesses"
              {...register("maxGuesses", {
                required: true,
                max: 10,
                min: 5,
                maxLength: 0,
              })}
            />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" type="submit">
            Apply
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
}
