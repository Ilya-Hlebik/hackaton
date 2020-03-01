import React, {Component, useCallback, useState} from 'react'
import {Card, Button, Form, FormLayout, Checkbox, TextField} from '@shopify/polaris';
//import { Link } from "react-router-dom"

import './Login.scss'

const Login = () => {

	const [newsletter, setNewsletter] = useState(false);
	const [email, setEmail] = useState('');

	const handleSubmit = useCallback((_event) => {
		setEmail('');
		setNewsletter(false);
		console.log('Save');
	}, []);

	const handleNewsLetterChange = useCallback(
		(value) => setNewsletter(value),
		[],
	);

	const handleEmailChange = useCallback((value) => setEmail(value), []);

	return (
		<div className='login-box'>
			<div className="login-title-box">
				<p>Login</p>
				<hr/>
			</div>
				<Form onSubmit={handleSubmit}>
					<FormLayout>
						<TextField
							value={email}
							onChange={handleEmailChange}
							label="Email"
							type="email"
						/>
						<TextField
							value={email}
							onChange={handleEmailChange}
							label="Password"
							type="password"
						/>
						<div className="remember-me-box">
							<Checkbox
								label="Remember me"
								checked={newsletter}
								onChange={handleNewsLetterChange}
							/>
						</div>
						<div className="submit-box">
							<Button primary submit>Submit</Button>
							<a href="#">Forgot Your Password?</a>
						</div>

					</FormLayout>
				</Form>
		</div>
	)
};

export default Login;