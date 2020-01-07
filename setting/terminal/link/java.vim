" 컴파일과 실행
map <F6> :!java %:r<SPACE>
map <F7> :w<ENTER>:make<ENTER>

" 컴파일 설정
" set makeprg=javac %
set errorformat=%A%f:%l:\ %m,%-Z%p^,%-C%.%#

" 컴파일 에러 찾아가기
map ,n :cn<ENTER>
map ,p :cp<ENTER>
map ,l :cl<ENTER>
map ,w :cw<ENTER>

" 블럭잡고 자동 주석처리
vmap ,c :s/^/\/\//g<ENTER>
vmap ,uc :s/^\/\///g<ENTER>

" TagList 설정
nnoremap <silent> <F8> :Tlist<CR>
nnoremap <silent> <F9> :w<CR>:TlistUpdate<CR>
let Tlist_Inc_Winwidth=0
let Tlist_Use_Right_Window=1

" ctags 설정
set tags=~/.javatags
set complete=.,w,b,u,t,i

" abbreviation 설정
ab sysout System.out.println();<ESC>hi
ab syserr System.out.println();<ESC>hi
ab debug if (log.isDebugEnabled()) {<CR>log.debug();<CR>}<CR><ESC>kkf(a
