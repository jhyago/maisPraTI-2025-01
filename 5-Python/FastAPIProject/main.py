import os
from typing import List, Literal, Optional

from fastapi import FastAPI, HTTPException
from fastapi.responses import HTMLResponse
from pydantic import BaseModel

try:
    from openai import OpenAI
except ImportError:
    OpenAI = None
app = FastAPI(title="Conversando com o Gepeto")

class ChatMessage(BaseModel):
    role: Literal["system", "user", "assistant"]
    content: str

from pydantic import Field

class ChatRequest(BaseModel):
    message: Optional[str] = None
    history: List[ChatMessage] = Field(default_factory=list)
    model: Optional[str] = None
    temperature: Optional[float] = 0.7

class ChatResponse(BaseModel):
    reply: str
    model: str
    usage: Optional[dict] = None

@app.get("/")
async def root():
    return {"message": "Hello World"}


@app.get("/hello/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}

@app.get("/chat", response_class=HTMLResponse)
async def chat_ui():
        html = """"
            <!doctype html>
            <html lang=\"pt-BR\">
            <head>
                <meta charset=\"utf-8\" />
                <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />
                <title>Chat com OpenAI</title>
                <style>
                body { font-family: system-ui, -apple-system, Segoe UI, Roboto, sans-serif; margin: 0; background: #0b1020; color: #e6e6e6; }
                header { padding: 12px 16px; background: #111634; position: sticky; top: 0; border-bottom: 1px solid #222a55; }
                header h1 { margin: 0; font-size: 18px; }
                #chat { max-width: 840px; margin: 16px auto; padding: 0 16px 90px; }
                .msg { padding: 12px 14px; border-radius: 12px; margin: 10px 0; white-space: pre-wrap; line-height: 1.35; }
                .user { background: #1e2a55; align-self: flex-end; }
                .assistant { background: #16213a; border: 1px solid #22305f; }
                .row { display: flex; }
                .col { flex: 1; }
                #composer { position: fixed; bottom: 0; left: 0; right: 0; background: #111634; border-top: 1px solid #222a55; padding: 10px 12px; }
                #input { width: 100%; max-width: 840px; margin: 0 auto; display: flex; gap: 8px; }
                textarea { flex: 1; resize: vertical; min-height: 38px; max-height: 180px; padding: 10px; border-radius: 8px; border: 1px solid #2a3770; background: #0c1433; color: #e6e6e6; }
                button { padding: 10px 14px; border-radius: 8px; border: 1px solid #2a3770; background: #20306a; color: #fff; cursor: pointer; }
                button:disabled { opacity: 0.6; cursor: not-allowed; }
                .system { color: #b9c4ff; font-size: 12px; margin: 8px 0 0 0; opacity: 0.8; }
                </style>
            </head>
        <body>
            <header>
                <h1>Chat com OpenAI</h1>
            </header>
            <main id=\"chat\"></main>
                <div id=\"composer\">
                <div id=\"input\">
                <textarea id=\"text\" placeholder=\"Escreva sua mensagem...\"></textarea>
                <button id=\"send\">Enviar</button>
                </div>
                </div>
        <script>
            const chat = document.getElementById('chat');
            const text = document.getElementById('text');
            const send = document.getElementById('send');
            let history = [];

        function addMessage(role, content) {
          const div = document.createElement('div');
          div.className = 'msg ' + role;
          div.textContent = content;
          chat.appendChild(div);
          window.scrollTo({ top: document.body.scrollHeight });
        }

        async function callChat(message) {
          const payload = { message, history };
          send.disabled = true;
          try {
            const res = await fetch('/api/chat', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(payload)
            });
            const data = await res.json();
            if (!res.ok) throw new Error(data.detail || 'Erro');
            // Update history
            history.push({ role: 'user', content: message });
            history.push({ role: 'assistant', content: data.reply });
            addMessage('assistant', data.reply);
          } catch (e) {
            addMessage('assistant', 'Falha: ' + e.message);
          } finally {
            send.disabled = false;
          }
        }

        send.addEventListener('click', () => {
          const msg = text.value.trim();
          if (!msg) return;
          addMessage('user', msg);
          text.value = '';
          callChat(msg);
        });

        text.addEventListener('keydown', (e) => {
          if ((e.metaKey || e.ctrlKey) && e.key === 'Enter') {
            send.click();
          }
        });
        </script>
        </body>
            </html>
        """
        return HTMLResponse(content=html)

@app.post("/api/chat", response_model=ChatResponse)
async def chat(req: ChatRequest):
    if OpenAI is None:
        raise HTTPException(status_code=503, detail="OpenAI not installed")

    api_key = ""

    messages = [{"role": m.role, "content": m.content} for m in req.history]
    if req.message:
        messages.append({"role": "user", "content": req.message})
    if not messages:
        raise HTTPException(status_code=400, detail="Envie 'message' ou 'history' com mensagens.")

    model = req.model or "gpt-4o-mini"
    temperature = req.temperature if req.temperature is not None else 0.7

    try:
        client = OpenAI(api_key=api_key)
        completion = client.chat.completions.create(
            model=model,
            messages=messages,
            temperature=temperature
        )
        reply = completion.choices[0].message.content if completion.choices else ""
        usage = getattr(completion, "usage", None)

        if usage is None:
            usage_dict = None
        else:
            usage_dict = getattr(usage, "model_dump", None)
            usage_dict = usage_dict() if callable(usage_dict) else getattr(usage, "__dict__", None)
        return ChatResponse(reply=reply, model=model, usage=usage_dict)
    except Exception as e:
        raise HTTPException(status_code=502, detail=f"Erro ao chamar a OpenAI: {e}")