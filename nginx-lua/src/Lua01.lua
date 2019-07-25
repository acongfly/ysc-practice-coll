--
--- Created by IntelliJ IDEA.
--- User: shicongyang
--- Date: 2019-06-11
--- Time: 18:10
--- To change this template use File | Settings | File Templates.
---  https://moonbingbing.gitbooks.io/openresty-best-practices/lua/class.html

--基本类型
--print(type("hello world")) -->output:string
--print(type(print))         -->output:function
--print(type(true))          -->output:boolean
--print(type(360.0))         -->output:number
--print(type(nil))           -->output:nil
--
--local num
--print(num)        -->output:nil
--
--num = 100
--print(num)        -->output:100

--################################
--boolean  布尔类型，可选值 true/false；Lua 中 nil 和 false 为“假”，其它所有值均为“真”。比如 0 和空字符串就是“真”；
local a = true
local b = 0
local c = nil
if a then
    print("a")        -->output:a
else
    print("not a")    --这个没有执行
end

if b then
    print("b")        -->output:b
else
    print("not b")    --这个没有执行
end

if c then
    print("c")        --这个没有执行
else
    print("not c")    -->output:not c
end

local order = 3.33
local score = 99.99
print(math.floor(order))   --向下取整 3
print(math.ceil(score))   --向上取整 100


