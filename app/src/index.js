import React from 'react'
import ReactDOM from 'react-dom'
import '@shopify/polaris/styles.css';
import enTranslations from '@shopify/polaris/locales/en.json';
import {AppProvider} from '@shopify/polaris';

import { BrowserRouter } from "react-router-dom"

import './index.scss'

import App from './App'

ReactDOM.render((

		<BrowserRouter>
			<AppProvider i18n={enTranslations}>
				<App/>
			</AppProvider>
		</BrowserRouter>

	), document.getElementById('app')
);