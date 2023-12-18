## Result preview

<img width="1303" alt="frame" src="https://github.com/Antony404/Charify/assets/20972206/5c15a84c-5c9e-45f8-a5d7-d58c4476b13b">

<img width="883" alt="image" src="https://github.com/Antony404/Charify/assets/20972206/e9e406d4-13f7-4fad-a668-b905a10423d4">

ffmpeg command to concat images into video:
```shell
ffmpeg -framerate 24 -pattern_type glob -i '*.png' -c:v libx264  out.mp4
```
