import{g as a}from"./main-9FmcGLMi.js";const u=()=>{const e=document.querySelector("form"),n=a("auth");if(!e)throw new Error("Form not found");e.addEventListener("submit",async r=>{var t,o;r.preventDefault();const s=(t=document.getElementById("username"))==null?void 0:t.value,m=(o=document.getElementById("password"))==null?void 0:o.value;n.logIn(s,m)})};u();