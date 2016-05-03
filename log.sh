#!/bin/bash

echo "hello world"

LOG_BASE_DIR=/home/tangyang/share/log/
LOG_TMP_FILE=tmp.log
LOG_TMP_CUT_FILE=tmp_cut.log

function doHelp()
{
	echo "common list"
	echo "help:                  show common list"
	echo "tot_req_num :          all request number"
	echo "ip_req_num :           all ip request number"
	echo "ip_req_top:            ip request number top10"
	echo "fun_top:               interface used top20"
	echo "fun_tail:              interface used tail20"
	echo "fun_num \$funName:      interface used number"
	echo "fun_exec:              interface exec time tail20"
	echo "fun_failed:            interface failed tail20"
	echo "fun_status \$funName:   interface failed status"
	echo "exit:                  exit"
}

function init()
{
	cd $LOG_BASE_DIR
	rm $LOG_TMP_FILE -Rf
	rm $LOG_TMP_CUT_FILE -Rf
	find -name "*.log" -exec 'cat' {} \; > $LOG_TMP_FILE
	cat $LOG_TMP_FILE |awk '/requestlog/ {print}' > $LOG_TMP_CUT_FILE
	rm $LOG_TMP_FILE -Rf
}

function exit()
{
	rm $LOG_TMP_FILE -Rf
	rm $LOG_TMP_CUT_FILE -Rf
	cd -
}

function tot_req_num()
{
	awk -F "|" '/requestlog/ {print $4}' $LOG_TMP_CUT_FILE |sort|wc -l
}

function ip_req_num()
{
	awk -F "|" '/requestlog/ {print $4}' $LOG_TMP_CUT_FILE |sort|uniq|wc -l
}

function ip_req_top()
{
	awk -F "|" '/requestlog/ {print $4}' $LOG_TMP_CUT_FILE |sort|uniq -c |sort -nr |head -10
}

function fun_top()
{
	awk -F "|" '/requestlog/ {print $5}' $LOG_TMP_CUT_FILE |sort|uniq -c |sort -nr |head -20
}

function fun_tail()
{
	awk -F "|" '/requestlog/ {print $5}' $LOG_TMP_CUT_FILE |sort|uniq -c |sort -n |head -20
}

function fun_num()
{
	awk -F "|" '/requestlog/ {print $5}' $LOG_TMP_CUT_FILE |grep $1|wc -l
}

function fun_exec()
{
	awk -F "|" '/requestlog/ {a[$5]+=$6;b[$5]++}END{for(i in a)print i,b[i],int(a[i]/b[i])}' $LOG_TMP_CUT_FILE |sort -k3 |head -20
}

function fun_failed()
{
	awk -F "|" '/requestlog/&&$7!=000 {print $5}' $LOG_TMP_CUT_FILE |sort|uniq -c |sort -nr |head -20
}

function fun_status()
{
	awk -F "|" '/requestlog/&&$7!=000 {print}' $LOG_TMP_CUT_FILE |grep $1 |awk -F "|" '{print $7}' |sort|uniq -c |sort -nr |head -20
}

init

while :
do
	read commod commod1
	if [ ${commod} == "exit" ]
		then
			exit
			break
	elif [ ${commod} == "tot_req_num" ]
		then
			tot_req_num
			continue
	elif [ ${commod} == "ip_req_num" ]		
		then
			ip_req_num
			continue
	elif [ ${commod} == "ip_req_top" ]
		then
			ip_req_top
			continue
	elif [ ${commod} == "fun_top" ]
		then
			fun_top
			continue
	elif [ ${commod} == "fun_tail" ]
		then
			fun_tail
			continue
	elif [ ${commod} == "fun_num" ] && [ ${commod1} ]
		then
			fun_num $commod1
			continue 
	elif [ ${commod} == "fun_exec" ]
		then
			fun_exec
			continue
	elif [ ${commod} == "fun_failed" ]
		then
			fun_failed
			continue
	elif [ ${commod} == "fun_status" ] && [ ${commod1} ]
		then
			fun_status $commod1
			continue
	else
		doHelp
	fi

done

