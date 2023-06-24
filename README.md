# server

### 봐글봐글

<img src = "https://github.com/readubble/server/assets/87148580/8f6a6c6a-1f3a-4e0a-9043-9f9dcd632565" width="30%" height="30%">

최근 사회적으로 대두되고 있는 '문해력 논란'과 관련하여 이를 해결하고자 문해력 교육용 어플리케이션을 기획하였음.

### Skill
- client: flutter
- server: spring boot
- DB: MySQL
- infra: AWS EC2, AWS S3, AWS CodeDeploy
- etc(외부 API 등): Chat-GPT API, seeso SDK, 국립국어원 표준국어대사전 API

### Feature
- 사용자가 어휘퀴즈를 풀 수 있도록 함. 하루에 3개를 풀 수 있으며 매일 갱신됨.
- 사용자가 글을 읽고 스스로 키워드, 주제문을 찾고 요약문을 작성하도록 하여 능동적인 학습을 유도하였으며 추가적으로 글의 이해도 확인을 위해 문제를 풀도록 함. 이때 요약문은 본인이 작성한 요약문과 비교를 해볼 수 있는 예시답안용 요약문을 제시함.
- 사용자가 글을 읽는 과정에서 Eye Tracking을 시행하며, 추적 종료 후엔 시선이 머물렀던 곳 중 최빈값 20곳을 시각화하여 보여줌.
- 글을 읽는 과정에서 모르는 단어가 있다면 검색할 수 있도록 사전 기능을 제공함(국립국어원 표준국어대사전 API 활용)
- 마음에 들었던 글, 반복해서 학습하고싶은 단어를 다시 볼 수 있도록 북마크 기능을 제공함.
- 글을 읽고 문제를 생성하는 과정, 비교용 요약문 생성을 자동화하기 위하여 Chat-GPT API를 활용하여 문제를 생성하고 요약문을 생성함.

### Architecture
![images](https://github.com/readubble/server/assets/87148580/30c6a2e7-394f-4aad-ae73-4697030225bd)
![images](https://github.com/readubble/server/assets/87148580/c022bd2a-3223-4a88-a948-e44ee61123a5)
![images](https://github.com/readubble/server/assets/87148580/b8747b65-9668-41b2-85d1-f9e97079a2b4)

### Contributor

<table width="900">
<thead>
    <tr>
        <th width="70" align="center">Name</th>
		<th width="80" align="center">Department</th>
        <th width="250" align="center">Role</th>
        <th width="150" align="center">Github</th>
    </tr> 
</thead>

<tbody>
    <tr>
       <td width="70" align="center">공예지</td>
		<td width="80" align="center">소프트웨어학부</td>
        <td width="250" align="center">server/API</td>
        <td width="150" align="center">	
	        <a href="https://github.com/YejiGong">
	            <img src="https://img.shields.io/badge/YejiGong-655ced?style=social&logo=github"/>
	        </a>
        </td>
    </tr>
    <tr>
        <td width="70" align="center">김유미</td>
		<td width="250" align="center">소프트웨어학부</td>
        <td width="250" align="center">client</td>
        <td width="150" align="center">	
	        <a href="https://github.com/imyoumikim">
	            <img src="https://img.shields.io/badge/imyoumikim-655ced?style=social&logo=github"/>
	        </a>
        </td>
    </tr>
    <tr>
        <td width="70" align="center">김진성</td>
		<td width="250" align="center">소프트웨어학부</td>
        <td width="250" align="center">server/DB</td>
        <td width="150" align="center">	
	        <a href="https://github.com/KimJinSe0ng">
	            <img src="https://img.shields.io/badge/KimJinSe0ng-655ced?style=social&logo=github"/>
	        </a>
        </td>
    </tr>
    <tr>
   
</tbody>
</table>
<br>
<br>
