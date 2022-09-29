import React, { useEffect, useState, useRef } from 'react';
import * as WebBrowser from 'expo-web-browser';
import * as Google from 'expo-auth-session/providers/google';
import { Text, View, Button, Image } from 'react-native';
import { GOOGLE_EXPO_CLIENT_ID, GOOGLE_ANDROID_CLIENT_ID, GOOGLE_WEB_CLIENT_ID } from '@env';
import Axios from 'axios';
import styled from 'styled-components/native';
import { HOST } from '@env';
import { useDispatch } from 'react-redux';
import { setAccessToken, setEmail, setNickname, setRefreshToken } from '../../redux/actions';
import * as Font from 'expo-font';

WebBrowser.maybeCompleteAuthSession();

function LoginScreen({ navigation }) {
  const [reqError, setReqError] = useState('');
  const [userEmail, setUserEmail] = useState();
  const [isFirst, setIsFirst] = useState('');
  const [nname, setNname] = useState('');
  const [aToken, setAToken] = useState('');
  const [rToken, setRToken] = useState('');

  const dispatch = useDispatch();

  const [request, response, promptAsync] = Google.useAuthRequest({
    expoClientId: GOOGLE_EXPO_CLIENT_ID,
    androidClientId: GOOGLE_ANDROID_CLIENT_ID,
    webClientId: GOOGLE_WEB_CLIENT_ID,
  });

  useEffect(() => {
    if (response?.type === 'success') {
      const { authentication } = response;
      // console.log(authentication);
      getGoogleUser(authentication.accessToken);
      // giveGoogleUser(authentication.accessToken);
    }
  }, [response]);

  // 이메일 값이 갱신되면 백에 요청
  const mountedEmail = useRef(false);
  useEffect(() => {
    if (!mountedEmail.current) {
      mountedEmail.current = true;
    } else {
      requireBack(userEmail);
    }
  }, [userEmail]);

  // redux state에 저장
  const saveReduxState = () => {
    dispatch(setNickname(nname));
    dispatch(setEmail(userEmail));
    dispatch(setRefreshToken(rToken));
    dispatch(setAccessToken(aToken));
  };
  // isFirst 값이 갱신되면 실행, 처음 로그인이면 추가정보입력으로 이동, 아닐 시 redux-secureStore에 토큰, 정보들 저장
  useEffect(() => {
    if (isFirst) {
      navigation.navigate('Nickname', { email: userEmail });
    } else if (isFirst === false) {
      saveReduxState();
    }
  }, [isFirst, navigation, saveReduxState, userEmail]);

  // 구글에 요청해서 email만 받아와서 state에 저장
  const getGoogleUser = async (accessToken) => {
    try {
      await Axios.get('https://www.googleapis.com/oauth2/v2/userinfo', {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
        .then(function (response) {
          setUserEmail(response.data.email);
        })
        .catch(function (error) {
          console.log(error);
        });
      // console.log(gUserReq.data);
      // setGUser(gUserReq.data);
      // storageData();
    } catch (error) {
      console.log('GoogleUserReq error: ', error.response.data);
      setReqError(error.response.data);
    }
  };
  // state에 저장된 email을 identifier로 써서 백에 데이터 조회
  const requireBack = async (mail) => {
    await Axios.get(`${HOST}/api/v1/members/login?identifier=${mail}`)
      .then(function (response) {
        console.log(response.data);
        setNname(response.data.nickname);
        setAToken(response.data.accessToken);
        setRToken(response.data.refreshToken);
        setIsFirst(response.data.isFirst);
      })
      .catch(function (error) {
        console.log(error);
      });
  };
  return (
    <LoginContainer>
      <ImageView>
        <Image
          source={require('../../../assets/image/logo.png')}
          style={{ width: '75%', marginLeft: '10%' }}
          resizeMode={'contain'}
        />
      </ImageView>

      <MiddleContainer>
        <MiddleText>책으로</MiddleText>
        <MiddleText>연결되는</MiddleText>
        <MiddleText>우리,</MiddleText>
        <MiddleText> </MiddleText>
        <MiddleText>책크인</MiddleText>
      </MiddleContainer>

      <GoogleLogin
        disabled={!request}
        title='Login'
        onPress={() => {
          promptAsync();
        }}
      >
        <Image source={require('../../../assets/image/google.png')} style={{ width: '12%', height: '40%' }} />
        <ButtonText>Google로 로그인</ButtonText>
      </GoogleLogin>
    </LoginContainer>
  );
}

const ImageView = styled.View`
  height: 30%;
`;

const LoginContainer = styled.View`
  flex: 1;
  background-color: #b1d8e8;
`;

const GoogleLogin = styled.TouchableOpacity`
  margin: 10% 20% 0;
  background-color: white;
  width: 60%;
  height: 9%;
  border-radius: 15px;
  justify-content: space-around;
  align-items: center;
  flex-direction: row;
  border: 1px solid #000;
`;

const MiddleContainer = styled.View`
margin-top: 35%;
  margin-left: 20%
  margin-right: auto
`;

const MiddleText = styled.Text`
  font-size: 20px;
  font-family: Light;
`;

const ButtonText = styled.Text`
  font-size: 16px;
  font-family: Medium;
`;

export default LoginScreen;
