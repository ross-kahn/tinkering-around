require 'sinatra'
require 'active_record'

get '/hi' do
  "Hello World!"
end


class Alarm < ActiveRecord::Base	
end

get '/alarms' do 
	Alarm.establish_connection(
		:adapter => "sqlite3",
		:database => "groupalarm"
	)
	
end