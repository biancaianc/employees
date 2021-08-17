resource "aws_instance" "web" {
  ami           = "ami-05f2e12c66ed5c986"
  instance_type = "t2.micro"
  iam_instance_profile = aws_iam_instance_profile.iam_profile.name
  vpc_security_group_ids = ["sg-0e3449ac9bda66de9"]
}

resource "aws_iam_instance_profile" "iam_profile" {
  name = "test_role"
  role = aws_iam_role.test_role.name
}