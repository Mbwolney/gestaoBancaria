CREATE TABLE conta (
    numero_conta INT PRIMARY KEY,
    saldo DECIMAL(15, 2) NOT NULL CHECK (saldo >= 0)
);

CREATE TABLE transacao (
    id SERIAL PRIMARY KEY,
    forma_pagamento CHAR(1) NOT NULL CHECK (forma_pagamento IN ('P', 'C', 'D')),
    valor DECIMAL(15, 2) NOT NULL CHECK (valor > 0),
    numero_conta INT NOT NULL,
    CONSTRAINT fk_transaction_account FOREIGN KEY (numero_conta) REFERENCES conta(numero_conta)
);